<script lang="ts" setup>
import { RouterLink } from "vue-router";
import { ref, watch, onMounted } from "vue";
import { User } from "@/types";
import { useUserStore } from "@/store/user";
import Auth from "@/components/appbar/Auth.vue";
import NotAuth from "@/components/appbar/NotAuth.vue";

const userStore = useUserStore();

const user = ref<User>(userStore.user);
watch(
  () => userStore.user,
  (newUser) => {
    user.value = newUser;
  }
);
</script>
<template>
  <v-app-bar :elevation="0">
    <RouterLink to="/">
      <v-img
        class="logo"
        width="126px"
        height="48px"
        position="left"
        src="@/assets/logo-1.svg"
      />
    </RouterLink>
    <v-spacer />
    <!-- 이용방법 모달 버튼 -->
    <v-dialog max-width="400">
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          v-bind="activatorProps"
          text="이용 방법"
          variant="flat"
          style="color: blue"
        ></v-btn>
      </template>

      <template v-slot:default="{ isActive }">
        <v-card
          title="서비스 이용 방법"
          style="font-size: 35px; text-align: center"
          class="card_content"
        >
          <v-card-text style="">
            하나, 서비스 이용하기 <br />
            둘, 동요 선택하기 <br />
            셋, 동화 생성하기 <br />
            넷, 키워드 선택하기 <br />
            다섯, 생성하기
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn
              text="닫기"
              @click="isActive.value = false"
              style="font-size: 20px"
            ></v-btn>
          </v-card-actions>
        </v-card>
      </template>
    </v-dialog>
    <RouterLink to="/songList">
      <v-btn text="서비스 이용하기"></v-btn>
    </RouterLink>
    <Auth v-if="user" :user="user" />
    <NotAuth v-else />
  </v-app-bar>
</template>
<style>
.logo {
  margin: 50px;
}

.card_content {
  background: url("../../assets/background.gif");
  background-size: cover;
}
</style>
