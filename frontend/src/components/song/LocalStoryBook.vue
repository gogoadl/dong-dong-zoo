<script setup lang="ts">
import { defineProps, onMounted, onUnmounted, ref, watch } from "vue";
import AudioPlayer from "vue3-audio-player";
import "vue3-audio-player/dist/style.css";
import { RefSongDetail } from "@/types";

const assetPath = import.meta.env.VITE_ASSET_PATH;
console.log(assetPath);
const props = defineProps<{
  songDetail: RefSongDetail;
  pageNumber: number;
}>();

let currentPage = 1;
let interval: any;

onMounted(() => {
  console.log(props.pageNumber);
  setTimeout(() => {
    (<any>$("#book")).turn({
      gradients: true,
      acceleration: true,
      pages: props.pageNumber,
    });
    interval = setInterval(() => {
      (<any>$("#book")).turn("page", currentPage++);
      if (currentPage > props.pageNumber) {
        console.log(currentPage);
        clearInterval(interval);
      }
    }, 1000);
  }, 500);
});

onUnmounted(() => {
  if (interval !== undefined) {
    clearInterval(interval);
  }
});
</script>

<template>
  <v-col class="col" cols="12">
    <h1 class="title">동화 읽어보기</h1>
    <div class="container">
      <div id="book">
        <!-- 각 페이지에 대한 v-for 루프 -->
        <!-- 여기에 6 대신에 props.pageNumber를 넣으면 2페이지 이후부터는 생성이 되지 않는데 왜 그런건지 도저히 모르겠습니다... -->
        <!-- props 받은 후 생성 vs props 받기 전 생성 시점 차이 -->
        <div v-for="index in props.pageNumber" class="page">
          <!-- 현재 페이지의 이미지 -->
          <v-img
            :src="`${assetPath}${props.songDetail?.songImageUrl}${index}.png`"
          ></v-img>
        </div>
      </div>
      <v-col align="center" justify="center">
        <v-col cols="8">
          <AudioPlayer
            :option="{
              src: `${assetPath}${props.songDetail?.songFileUrl}`,
              title: `${props.songDetail?.songName}`,
            }"
          />
        </v-col>
      </v-col>
    </div>
  </v-col>
</template>
<style scoped>
.col {
  margin: 24px;
  padding: 24px;
  border-radius: 24px;
  background-color: rgb(239, 239, 239);
}

.title {
  margin-left: 24px;
}

.container {
  overflow: hidden;

  #book {
    margin: 5% auto;
    width: 800px;
    height: 400px;
  }
}

#book .turn-page {
  -webkit-background-size: 100% 100%;
  background-size: 100% 100%;
}

#book div {
  -webkit-background-size: cover;
  background-size: cover;
  background-position: center center;
}
</style>
