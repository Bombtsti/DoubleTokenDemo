import {defineStore} from 'pinia'
import {ref} from "vue";
import {storage} from "@/utils/storage.js";
import {refreshToken} from "@/api/user.js";

export const useUserStore = defineStore('userStore',()=>{
    const token = ref("");

    const getToken = ()=>{
      return storage.get("accessToken");
    };

    const getUserInfo = ()=>{
      return storage.get("userInfo");
    };

    const setUserInfo = (userInfo)=>{
      storage.set("userInfo",userInfo);
    };

    const isLogin = ()=>{
      const user = storage.get("userInfo");
      return user!=null;
    };

    const getNewToken = (token)=>{
      return new Promise((resolve, reject)=>{
          refreshToken(token).then((res)=>{
              resolve(res);
          }).catch((error)=>{
              reject(error);
          });
      });
    };

    const loginOut = ()=>{
      window.localStorage.clear();
    };

    return { token, getToken, getNewToken, getUserInfo, setUserInfo, loginOut, isLogin };
})