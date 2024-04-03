<script setup lang="ts">
import SongCard from "./SongCard.vue";
import { ref, onMounted } from "vue";
import { useSongStore } from "@/store/song";
import { HttpStatusCode } from "axios";
import { RefSong } from "@/types";

const songStore = useSongStore();

const songList = ref<RefSong[]>(null);
onMounted(() => {
  songStore.getSongs(
    (res) => {
      if (res.status == HttpStatusCode.Ok) {
        songList.value = res.data;
      }
    },
    (err) => {
      console.log(err);
    }
  );
});
</script>

<template>
  <div class="songTitle">
    <h1 style="">동요 리스트</h1>
  </div>

  <v-container class="list">
    <div class="songlist">
      <p v-for="song in songList" style="margin: 24px">
        <SongCard :song="song"></SongCard>
      </p>
    </div>
  </v-container>
</template>

<style scoped>
.songTitle {
  text-align: center;
}

.list {
  justify-content: center;
}

.songlist {
  display: grid;
  justify-content: center;
  grid-template-columns: repeat(3, 1fr);
}

.song_content {
  width: 50px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
