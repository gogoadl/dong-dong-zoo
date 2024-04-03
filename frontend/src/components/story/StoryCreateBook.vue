<script setup lang="ts">
import { defineProps, ref, onMounted, onUnmounted, watch } from "vue";
import ServerStoryBook from "../song/ServerStoryBook.vue";
import AudioPlayer from "vue3-audio-player";
import "vue3-audio-player/dist/style.css";
import { useRoute, useRouter } from "vue-router";
import { useSongStore } from "@/store/song";
import { HttpStatusCode } from "axios";
import { RefStoryImageRes, RefSongDetail } from "@/types/";
import { useLoadingStore } from "@/store/loading";

const loadingStore = useLoadingStore();
const route = useRoute();
const songId = route.params.songId;
const tag = route.params.tag;
const { songName, songUrl } = history.state;
const songStore = useSongStore();

console.log(songName);
console.log(songUrl);
// 새롭게 생성된 이미지 url 리스트
const storyImages = ref<RefStoryImageRes[]>(null);

loadingStore.contentLoading();
songStore.createSong(
  songId as string,
  tag as string,
  (res) => {
    if (res.status == HttpStatusCode.Ok) {
      console.log(res.data);
      storyImages.value = res.data;
      console.log(storyImages.value);
      loadingStore.contentLoaded();
    }
  },
  (err) => {
    console.error(err);
    loadingStore.contentLoaded();
  }
);

// StoryBook
</script>
<template>
  <v-container>
    <template v-if="storyImages">
      <ServerStoryBook
        :pageNumber="storyImages.length"
        :storyImages="storyImages"
        :songName="songName"
        :songUrl="songUrl"
      />
    </template>
  </v-container>
</template>

<style scopeds>
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
