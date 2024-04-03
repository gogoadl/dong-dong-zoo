<script setup lang="ts">
import { RouterLink } from "vue-router";
</script>

<template>
  <RouterLink to="/login">
    <v-btn text="로그인" class="login_btn"></v-btn>
  </RouterLink>
  <!-- <RouterLink to="/signup">
    <v-btn text="회원가입"></v-btn>
  </RouterLink> -->
</template>

<style scoped>
.login_btn {
  font-size: 25px;
}
</style>
