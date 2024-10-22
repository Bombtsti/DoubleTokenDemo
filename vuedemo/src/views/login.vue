<template xmlns="http://www.w3.org/1999/html">
  <div class="loginForm">
    <div class="username">
      账号：<input placeholder="输入账号" type="text"  v-model="userLogin.username" />
    </div>
    <div class="password">
      密码：<input placeholder="输入密码" type="password"  v-model="userLogin.password"/>
    </div>
    <div class="loginBtn">
      <button @click="loginMethod">登录</button>
    </div>

    <div>
      <span>测试账号：zlw</span>
    </div>
    <div>
      <span>测试密码：123123</span>
    </div>
  </div>
</template>

<script setup>
  import {ref} from "vue";
  import {login} from "@/api/user.js";
  import {storage} from "@/utils/storage.js";
  import router from "@/router/index.js";
  import {useUserStore} from "@/store/userStore.js";

  const userStore = useUserStore();

  const userLogin = ref({
    username:"",
    password:""
  })

  const loginMethod = ()=>{
    console.log("denglu");
    login(userLogin.value).then((res)=>{
      console.log(res)
      storage.set("accessToken",res.data.accessToken);
      storage.set("refreshToken",res.data.refreshToken);
      userStore.setUserInfo(res.data.userInfo);
      console.log(res.data.accessToken);
      router.push({path:"/"});
    }).catch((error)=>{
      console.log("error");
      console.log(error);
    });
  }

</script>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
