<script setup lang="ts">
import { defineProps, ref, watch, onMounted } from "vue";
import type { User } from "@/types";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { useUserStore } from "@/store/user";
import { useRouter } from "vue-router";
const userStore = useUserStore();
const router = useRouter();
const props = defineProps<{ user: User }>();

const user = ref<User>(null);

const drawerState = ref(false);

watch(
  () => props.user,
  (newVal) => {
    user.value = newVal;
  }
);

const toggleState = () => {
  drawerState.value = !drawerState.value;
};

const myFairyTales = () => {
  toggleState();
};

const mySongs = () => {
  toggleState();
  router.push("/mySongList");
};

const logout = () => {
  toggleState();
  userStore.logout();
  router.push("/");
};
</script>

<template>
  <v-btn class="container" @click="toggleState" id="menu-activator">
    <v-menu activator="#menu-activator">
      <v-list>
        <!-- <v-list-item>
          <v-btn elevation="0" class="menu-btn" @click="myFairyTales">
            <v-list-item-title
              ><font-awesome-icon
                :icon="['fas', 'book-open']"
                style="margin-top: 8px; margin-right: 10px"
              />내가 만든 동화</v-list-item-title
            >
          </v-btn>
        </v-list-item> -->
        <v-list-item>
          <v-btn elevation="0" class="menu-btn" @click="mySongs">
            <v-list-item-title>
              <font-awesome-icon
                :icon="['fas', 'book-open']"
                style="margin-top: 8px; margin-right: 10px"
              />
              동요 찜 목록</v-list-item-title
            >
          </v-btn>
        </v-list-item>
        <v-list-item>
          <v-btn elevation="0" class="menu-btn" @click="logout">
            <v-list-item-title>
              <font-awesome-icon
                :icon="['fas', 'power-off']"
                style="margin-top: 8px; margin-right: 10px"
              />
              로그아웃</v-list-item-title
            >
          </v-btn>
        </v-list-item>
      </v-list>
    </v-menu>
    <v-row align="center" justify="center">
      <v-col cols="3"
        ><div
          style="
            background-color: white;
            width: 24px;
            height: 24px;
            border-radius: 50%;
          "
        >
          <font-awesome-icon :icon="['fas', 'user']" style="margin-top: 4px" />
        </div>
      </v-col>
      <v-col cols="auto">동동이</v-col>
      <v-col cols="3">
        <transition name="fade">
          <div>
            <font-awesome-icon
              v-if="!drawerState"
              :icon="['fas', 'bars']"
              style="width: 16px; height: 16px"
            />
            <font-awesome-icon
              v-else
              :icon="['fas', 'arrow-up']"
              style="width: 16px; height: 16px"
            />
          </div>
        </transition>
      </v-col>
    </v-row>
  </v-btn>
</template>

<style scoped>
.container {
  background-color: #7aafff;
  padding: 10px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  border-top-left-radius: 0px;
  border-top-right-radius: 0px;
}
.menu-btn {
  display: flex;
  flex-direction: column;
  align-items: start;
  width: 100%;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
