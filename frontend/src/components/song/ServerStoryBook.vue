<script setup lang="ts">
import { defineProps, onMounted, onUnmounted, ref, watch } from "vue";
import AudioPlayer from "vue3-audio-player";
import "vue3-audio-player/dist/style.css";
import { StoryImageRes, AudioPlayerComponent } from "@/types";

const player = ref<AudioPlayerComponent>(null);

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
let playTime = 0;
let nextPageTime = 0;
let pageOffset = 0;

onMounted(() => {
  console.log(player.value.totalTime);
  const timestamp = props.storyImages.map((story) => story.startTime);
  console.log(timestamp);
  player.value.onAudioPlay = () => {
    if (playTime == 0) {
      playTime = player.value.totalTime;
      nextPageTime = playTime / props.pageNumber;
    }
  };
  player.value.onTimeUpdate = (event) => {
    if (timestamp[0]) {
      let splited = timestamp[0].split(":");
      const seconds = Number(splited[0]) * 60 + Number(splited[1]);
      console.log(player.value.currentTime);
      console.log(seconds);
      if (seconds < player.value.currentTime) {
        timestamp.shift();
        (<any>$("#book")).turn("page", currentPage++);
      }
    }
  };

  player.value.onAudioEnded = () => {
    playTime = 0;
    nextPageTime = 0;
    pageOffset = 0;
    currentPage = 1;
    (<any>$("#book")).turn("page", currentPage++);
  };
  console.log(props.pageNumber);
  setTimeout(() => {
    (<any>$("#book")).turn({
      gradients: true,
      acceleration: true,
      pages: props.pageNumber,
    });
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
    <div class="container" v-if="props.storyImages">
      <div id="book">
        <div v-for="image in storyImages" class="page">
          <v-img :src="image.imageURL"></v-img>
        </div>
      </div>
      <v-col align="center" justify="center">
        <v-col cols="8">
          <AudioPlayer
            ref="player"
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
