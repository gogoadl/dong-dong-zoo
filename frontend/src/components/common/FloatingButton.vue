<script setup lang="ts">
import { onMounted } from "vue";
const toTop = () => {
  window.scrollTo({ top: 0, left: 0, behavior: "smooth" });
};
onMounted(() => {
  toTop();
});
</script>
<template>
  <div class="floating-button-div">
    <button class="fb" @click="toTop"><v-icon> mdi-arrow-up </v-icon></button>
  </div>
</template>
<style>
.floating-button-div {
  position: absolute;
  z-index: 33333;
  position: fixed;
  bottom: 20px;
  right: 20px;
}

.fb {
  z-index: 33333;
  background-color: #42a5f5;
  width: 64px;
  height: 64px;
  color: white;
  border: none;
  border-radius: 100%;
  padding: 20px;
  font-size: 16px;
  cursor: pointer;
  box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.5);
}
</style>
