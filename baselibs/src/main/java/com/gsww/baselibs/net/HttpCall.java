package com.gsww.baselibs.net;


import com.gsww.baselibs.BaseApplication;
import com.gsww.baselibs.net.netlistener.CallBackLis;
import com.gsww.baselibs.utils.Logger;
import com.gsww.baselibs.utils.NetWorkUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class HttpCall {


    private static OnTokenPastListener onTokenPastListener;
    public interface OnTokenPastListener{
        void onTokenListener(String msg);
    }

    public static void setOnTokenPastListener(OnTokenPastListener onTokenPastListener) {
        HttpCall.onTokenPastListener = onTokenPastListener;
    }

    /**
     * 网络请求统一封装
     *
     * @param observable
     * @param callBackLis 网络请求回调
     * @param flag        区分不同请求
     * @param <T>
     */
    public static <T> void doCall(Observable<ResponseModel<T>> observable, final CallBackLis<T> callBackLis, final String flag, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {

        if (observable == null || callBackLis == null) {
            throw new IllegalArgumentException("doCall方法前两个参数不能为空");
        }
        if(!NetWorkUtil.isNetworkConnected(BaseApplication.getInstance())){
            callBackLis.onFailure(flag,"没有可用的网络！");
            callBackLis.onComplete();
            return;
        }

        //被观察者_生命周期
        Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                lifecycleSubject.takeUntil(new Predicate<ActivityLifeCycleEvent>() {
                    @Override
                    public boolean test(ActivityLifeCycleEvent activityLifeCycleEvent) {
                        return activityLifeCycleEvent.equals(ActivityLifeCycleEvent.DESTROY);
                    }
                });

        //观察者_网络请求状态
        Observer<ResponseModel<T>> observer = new Observer<ResponseModel<T>>() {

            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(ResponseModel<T> baseModel) {
                try {
                    if (baseModel != null) {
                        if (baseModel.getResCode() == ResponseModel.SUCCESS) {
                            if (baseModel.getData() != null) {
                                callBackLis.onSuccess(flag, baseModel.getData());
                            } else {
                                callBackLis.onFailure(flag, "请求数据异常！");
                            }
                        } else {
                            if(baseModel.getResMsg().contains("无效用户")){
                                onTokenPastListener.onTokenListener(baseModel.getResMsg());
                                callBackLis.onFailure(flag, "个人信息有误");
                            }else{
                                callBackLis.onFailure(flag, baseModel.getResMsg());
                            }
                        }
                    } else {
                        callBackLis.onFailure(flag, "请求数据异常！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBackLis.onFailure(flag, "解析错误！");
                } finally {
                    callBackLis.onComplete();
                }
            }

            @Override
            public void onError(Throwable e) {
                try {
                    String error = e.getMessage();
                    Logger.d("onError: " + error);
                    if (error != null && (e.getMessage().contains("failed to connect to") || error.contains("HTTP 404"))) {
                        error = "服务器未响应，请稍后重试";
                    } else {
                        error = "连接超时";
                    }
                    callBackLis.onFailure(flag, error);
                }catch (Exception ee){
                    callBackLis.onFailure(flag, "服务异常");
                }finally {
                    callBackLis.onComplete();
                }
            }

            @Override
            public void onComplete() {
                //callBackLis.onComplete();
            }
        };

        //被观察者订阅观察者，根据生命周期取消订阅，子线程订阅主线程观察
        observable.takeUntil(compareLifecycleObservable).subscribeOn(Schedulers.newThread()).
                unsubscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);
    }

    /**
     * 网络请求统一封装
     *
     * @param observable
     * @param callBackLis 网络请求回调
     * @param flag        区分不同请求
     * @param <T>
     */
    public static <T> void doCallWithoutIntercept(Observable<T> observable, final CallBackLis<T> callBackLis, final String flag) {

        if (observable == null || callBackLis == null) {
            throw new IllegalArgumentException("参数为空");
        }
        if(!NetWorkUtil.isNetworkConnected(BaseApplication.getInstance())){
            callBackLis.onFailure(flag,"没有可用的网络！");
            callBackLis.onComplete();
            return;
        }
        //观察者_网络请求状态
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(T t) {
                try {
                    if (t != null) {
                        callBackLis.onSuccess(flag, t);
                    } else {
                        callBackLis.onFailure(flag, "请求数据异常！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBackLis.onFailure(flag, "解析错误！");
                }
            }

            @Override
            public void onError(Throwable e) {
               try {
                   String error = e.getMessage();
                   Logger.d("onError: " + error);
                   if (error != null && (e.getMessage().contains("failed to connect to") || error.equals("HTTP 404 Not Found"))) {
                       error = "服务器未响应，请稍后重试";
                   } else {
                       error = "连接超时";
                   }
                   callBackLis.onFailure(flag, error);
               }catch (Exception ee){
                   callBackLis.onFailure(flag, "服务异常");
               }finally {
                   callBackLis.onComplete();
               }
            }

            @Override
            public void onComplete() {
            }
        };
        observable.subscribeOn(Schedulers.newThread()).
                unsubscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);
    }


    /**
     * 网络请求统一封装
     *
     * @param observable
     * @param callBackLis 网络请求回调
     * @param flag        区分不同请求
     * @param <T>
     */
    public static <T> void doCallVService(Observable<VServiceResponseModel<T>> observable, final CallBackLis<T> callBackLis, final String flag,PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {

        if (observable == null || callBackLis == null) {
            throw new IllegalArgumentException("doCall方法前两个参数不能为空");
        }

        if(!NetWorkUtil.isNetworkConnected(BaseApplication.getInstance())){
            callBackLis.onFailure(flag,"没有可用的网络！");
            callBackLis.onComplete();
            return;
        }
        //观察者_网络请求状态
        Observer<VServiceResponseModel<T>> observer = new Observer<VServiceResponseModel<T>>() {

            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(VServiceResponseModel<T> baseModel) {
                try {
                    if (baseModel != null) {
                        if (baseModel.getCode() == VServiceResponseModel.SUCCESS) {
                            if (baseModel.getData() != null) {
                                callBackLis.onSuccess(flag, baseModel.getData());
                            } else {
                                callBackLis.onFailure(flag, "请求数据异常！");
                            }
                        } else {
                            if(baseModel.getMsg().contains("无效用户")){
                                onTokenPastListener.onTokenListener(baseModel.getMsg());
                                callBackLis.onFailure(flag, "个人信息有误");
                            }else{
                                callBackLis.onFailure(flag, baseModel.getMsg());
                            }
                        }
                    } else {
                        callBackLis.onFailure(flag, "请求数据异常！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBackLis.onFailure(flag, "解析错误！");
                } finally {
                    callBackLis.onComplete();
                }
            }

            @Override
            public void onError(Throwable e) {
                try {
                    String error = e.getMessage();
                    Logger.d("onError: " + error);
                    if (error != null && (e.getMessage().contains("failed to connect to") || error.contains("HTTP 404"))) {
                        error = "服务器未响应，请稍后重试";
                    } else {
                        error = "连接超时";
                    }
                    callBackLis.onFailure(flag, error);
                }catch (Exception ee){
                    callBackLis.onFailure(flag, "服务异常");
                }finally {
                    callBackLis.onComplete();
                }

            }

            @Override
            public void onComplete() {
                //callBackLis.onComplete();
            }
        };
        //被观察者订阅观察者，根据生命周期取消订阅，子线程订阅主线程观察
        observable
                .takeUntil(lifecycleSubject)
                .subscribeOn(Schedulers.newThread()).
                unsubscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);
    }
}
