import { defineStore } from "pinia";
import { computed, ref } from "vue";

export const useLoadingStore = defineStore("loading", () => {
  const contentLoadComplete = ref(true);

  const contentLoading = () => {
    console.log("contentLoading called");
    contentLoadComplete.value = false;
  };

  const contentLoaded = () => {
    console.log("contentLoaded called");
    contentLoadComplete.value = true;
  };

  const loading = computed(() => {
    console.log("loading called");
    console.log(contentLoadComplete.value);
    if (contentLoadComplete.value === false) {
      return true;
    } else {
      return false;
    }
  });

  return {
    loading,
    contentLoadComplete,
    contentLoading,
    contentLoaded,
  };
});
