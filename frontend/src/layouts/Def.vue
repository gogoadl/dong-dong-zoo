<script lang="ts" setup>
import DefaultBar from "./default/AppBar.vue";
import DefaultView from "./default/View.vue";
import Footer from "./default/Footer.vue";
import { useLoadingStore } from "@/store/loading";
import LoadingView from "@/components/common/LoadingView.vue";
</script>
<template>
  <v-app>
    <DefaultBar />
    d
    <DefaultView />
    <Footer />
    <LoadingView v-if="useLoadingStore().loading"></LoadingView>
  </v-app>
</template>
