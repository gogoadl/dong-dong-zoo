<script setup lang="ts">
import { defineProps, onMounted, onUnmounted, ref, watch } from "vue";
import AudioPlayer from "vue3-audio-player";
import "vue3-audio-player/dist/style.css";
import { StoryImageRes } from "@/types";

const assetPath = import.meta.env.VITE_ASSET_PATH;
console.log(assetPath);
const props = defineProps<{
  pageNumber: number;
  storyImages: StoryImageRes[];
  songName: string;
  songUrl: string;
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
  }, 100);
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
    <div class="container" v-if="props.storyImages">
      <div id="book">
        <div v-for="image in storyImages" class="page">
          <v-img :src="image.imageURL"></v-img>
        </div>
      </div>
      <v-col align="center" justify="center">
        <v-col cols="8">
          <AudioPlayer
            :option="{
              src: `${assetPath}${props.songUrl}`,
              title: `${props.songName}`,
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
