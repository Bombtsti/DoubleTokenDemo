
import axios from "axios";
import {useUserStore} from "@/store/userStore.js";
import {storage} from "@/utils/storage.js";

const baseURL = "http://localhost:8080/";
let isRefreshing = false;
let requestsQueue = [];

const service = axios.create({
    baseURL:baseURL,
    timeout:50000,
    headers:{"Content-Type":"application/json;charset=utf-8"}
});

//请求拦截器
service.interceptors.request.use((config)=>{
    const userStore = useUserStore();
    if(userStore.getToken){
        config.headers.accessToken = userStore.getToken();
    }
    return config;
},(error)=>{
    return Promise.reject(error);
});


//响应拦截器
service.interceptors.response.use((res)=> {
    console.log(res);
    if (res.data.code === 200) {
        return res.data;
    }

    const config = res.config;
    if(res.data.code===401){
        const userStore = useUserStore();
        if(!isRefreshing){
            isRefreshing = true;
            storage.set("accessToken","");
            const refreshToken = storage.get("refreshToken");
            return userStore.getNewToken(refreshToken).then(async (rftRes)=>{
                console.log(rftRes);
                if(rftRes.data.code===501){
                    window.location.href = "/login";
                }
                const accessToken = rftRes.data.accessToken;
                storage.set("accessToken",rftRes.data.accessToken);
                storage.set("refreshToken",rftRes.data.refreshToken);
                const firstReqRes = await service.request(config);
                requestsQueue.forEach((fuc)=>fuc(accessToken));
                requestsQueue = [];
                return firstReqRes;
            }).finally(()=>{
                isRefreshing = false;
            });
        }else{
            return new Promise((resolve)=>{
                requestsQueue.push((token)=>{
                    config.headers.accessToken = token;
                    resolve(service.request(config));
                });
            });
        }
    }
    return Promise.reject(res);

},(error)=>{
    console.log("登陆失败");
    window.localStorage.clear();
    window.location.href = "/login";
});


export default service;