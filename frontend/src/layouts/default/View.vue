<script lang="ts" setup>
import FloatingButton from "@/components/common/FloatingButton.vue";
import { useFloatingStore } from "@/store/floating";
import { onMounted, onUnmounted } from "vue";
const floatingStore = useFloatingStore();

const doScroll = (event: any) => {
  const el = event.target.documentElement;
  const scrollTop = el.scrollTop;
  if (scrollTop == 0) {
    floatingStore.showFloatingButton = false;
  } else {
    floatingStore.showFloatingButton = true;
  }
};

onMounted(() => {
  window.addEventListener("scroll", doScroll);
});

onUnmounted(() => {
  window.removeEventListener("scroll", doScroll);
});
</script>

<template>
  <v-main class="background">
    <router-view> </router-view>
    <transition name="slide-fade">
      <FloatingButton v-if="floatingStore.showFloatingButton" />
    </transition>
  </v-main>
</template>

<style scoped>
.background {
  background: url("../../assets/background.gif");
  background-size: cover;
  margin-top: 50px;
}
</style>
