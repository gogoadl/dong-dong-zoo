<script setup lang="ts">
import { ref, defineProps, watch } from "vue";
import { useRouter } from "vue-router";
import { RefSongDetail } from "@/types";
import SongDetail from "./SongDetail.vue";

const props = defineProps<{
  songDetail: RefSongDetail;
}>();

const songDetail = ref<RefSongDetail>(null);
let checkBtn = null;
let tag = ref();
let nowindex = ref();

const router = useRouter();

watch(
  () => props.songDetail,
  (newVal) => {
    songDetail.value = newVal;
    const list = Object.values(songDetail.value.songKeywordKoreanList);
    checkBtn = list.map((a) => ref(false));
  }
);

const highlightBtn = (index) => {
  checkBtn.forEach((element) => {
    element.value = false;
  });
  nowindex.value = index;
  checkBtn[index].value = !checkBtn[index].value;
  tag.value = props.songDetail.songKeywordList[index];
  console.log(props.songDetail.songKeywordList[index]);
  console.log(tag.value);
};

const clickBtn = () => {
  console.log(props.songDetail);
  console.log(props.songDetail.songKeywordList[nowindex.value]);
  router.push({
    name: "create",
    state: {
      songUrl: props.songDetail.songFileUrl,
      songName: props.songDetail.songName,
    },
    params: {
      tag: props.songDetail.songKeywordList[nowindex.value],
      songId: songDetail.value.songId,
    },
  });
};

let toggle = ref(false);
let audio = new Audio();
const startdongyo = () => {
  const url = "/src" + songDetail.value.songFileUrl;
  toggle.value = !toggle.value;
  if (toggle.value) {
    audio.src = url;
    audio.play();
  } else {
    audio.src = url;
    audio.pause();
  }
  console.log(toggle.value);
};

const stopdongyo = () => {
  const url = "/src" + props.songDetail.songFileUrl;
  let audio = new Audio(url);
  audio.pause();
  console.log("stopdongyo");
};
</script>

<template>
  <v-container align="center" justify="center">
    <v-row rows="12" class="ma-0 mt-2 pa-0">
      <v-row rows="12" class="detail-container" style="height: 250px">
        <v-col cols="6">
          <v-btn width="100%" height="100%" class="radius-12 song-preview">
            <h1
              class="mb-12"
              style="color: white; font-size: 48px"
              @click="startdongyo()"
            >
              동요 미리듣기
            </h1></v-btn
          >
        </v-col>

        <v-col cols="6" align="center" justify="center">
          <v-dialog transition="dialog-top-transition" width="auto">
            <template v-slot:activator="{ props: activatorProps }">
              <v-btn
                width="100%"
                height="100%"
                class="radius-12 create-story"
                size="large"
                rounded
                v-bind="activatorProps"
                text=""
                ><h1 class="mb-12" style="color: white; font-size: 48px">
                  동화 생성하기
                </h1></v-btn
              >
            </template>
            <template v-slot:default="{ isActive }">
              <v-card>
                <v-toolbar title="키워드"></v-toolbar>

                <v-card-text class="text-h3 pa-10" style="width: 1000px">
                  <v-row cols="12" md="1" sm="3">
                    <v-list-item
                      v-for="(list, index) in props.songDetail
                        .songKeywordKoreanList"
                      :key="index"
                    >
                      <v-btn
                        size="large"
                        rounded
                        v-if="checkBtn[index].value"
                        color="blue"
                        class="highlight-btn"
                        @click="highlightBtn(index)"
                        ><span class="option_content">{{ list }}</span></v-btn
                      >
                      <v-btn
                        size="large"
                        rounded
                        v-if="!checkBtn[index].value"
                        color="white"
                        class="highlight-btn"
                        @click="highlightBtn(index)"
                        ><span class="option_content">{{ list }}</span></v-btn
                      >
                    </v-list-item>
                  </v-row>
                </v-card-text>

                <v-card-actions class="justify-end">
                  <v-btn
                    size="large"
                    rounded
                    color="blue"
                    class="inner-btn"
                    text="생성하기"
                    @click="clickBtn"
                  ></v-btn>
                </v-card-actions>

                <!-- <v-card-actions class="justify-end">
                  <v-btn text="Close" @click="isActive.value = false"></v-btn>
                </v-card-actions> -->
              </v-card>
            </template>
          </v-dialog>
        </v-col>
      </v-row>
    </v-row>
  </v-container>
</template>

<style>
.inner-btn {
  justify-content: center;
}

.option_content {
  font-size: 25px;
}

.make_btn {
  max-width: 20%;
}

.highlight-btn {
  width: 200px;
  height: 100px;
  border-color: skyblue !important;
  color: black;
}
</style>
