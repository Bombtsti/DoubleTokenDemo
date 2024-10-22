<template>
  <main>
    <div class="users">
      <ul>
        <li v-for="user in users" :key="user.username">{{ user.username }}</li>
      </ul>
    </div>
  </main>
</template>

<script setup>
  import {ref} from "vue";
  import {getAllUsers} from "@/api/user.js";

  const users = ref([{
    "username":"",
    "password":""
  }]);

  const initData = ()=>{
    getAllUsers().then((res)=>{
      users.value = res.data;
    });
  }

  initData();
</script>