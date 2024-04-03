import { defineStore } from "pinia";
import { ref } from "vue";

export const useFloatingStore = defineStore("floating", () => {
  const showFloatingButton = ref(true);

  return {
    showFloatingButton,
  };
});
