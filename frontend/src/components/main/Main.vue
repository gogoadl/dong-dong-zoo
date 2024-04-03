<script setup lang="ts">
import { ref, onMounted, onUnmounted } from "vue";
import router from "@/router";

const showBanner1 = ref(false);
const showBanner2 = ref(false);
const showBanner3 = ref(false);

const doScroll = (event: any) => {
  const el = event.target.documentElement;
  const scrollTop = el.scrollTop;
  // console.log(scrollTop);
  if (scrollTop > 100 && !showBanner1.value) {
    showBanner1.value = true;
  } else if (scrollTop > 350 && !showBanner2.value) {
    showBanner2.value = true;
  } else if (scrollTop > 900 && !showBanner3.value) {
    showBanner3.value = true;
  }
};

const goSongList = () => {
  console.log("call goback");
  router.push("SongList");
};

onMounted(() => {
  window.addEventListener("scroll", doScroll);
  showBanner1.value = true;
});

onUnmounted(() => {
  window.removeEventListener("scroll", doScroll);
});
</script>

<template>
  <v-container>
    <v-col>
      <v-card class="card">
        <transition name="slide-fade">
          <v-row v-show="showBanner1">
            <v-img class="img" src="@/assets/character_1.png">
              <h1 class="inner-text-h1">
                동동주 서비스를 활용하여 <br />동요를 동화로 만들어 보세요!
              </h1>
              <h3 class="inner-text-h3">
                동동주의 서비스는 생성형 AI를 기반으로 동요를 동화로
                변환해줍니다.
              </h3>
            </v-img>
          </v-row>
        </transition>
      </v-card>
      <v-card class="card">
        <transition name="slide-fade">
          <v-row v-show="showBanner2">
            <v-img class="img" src="@/assets/character_2.png">
              <h1 class="inner-text-h1">
                동화를 보며 아이들의 <br />상상력을 자극시켜봐요!
              </h1>
              <h3 class="inner-text-h3">
                원하는 키워드를 이용하여 동화를 생성하고, <br />모든 사고능력의
                근간이 되는 상상력을 자극시켜 교육적 효과를 얻을 수 있어요!
              </h3>
            </v-img>
          </v-row>
        </transition>
      </v-card>
      <v-card class="card">
        <transition name="slide-fade">
          <v-row v-show="showBanner3">
            <v-img class="img" src="@/assets/character_3.png">
              <h1 class="inner-text-h1">지금바로 서비스를 이용하세요!</h1>
            </v-img>
            <v-btn @click="goSongList" size="large" rounded class="inner-btn">
              서비스 이용하기
            </v-btn>
          </v-row>
        </transition>
      </v-card>
    </v-col>
  </v-container>
</template>

<style scoped>
.img {
  border-radius: 50px;
  height: 100%;
  opacity: 70%;
}
.card {
  margin-top: 50px;
  border-radius: 50px;
  height: 500px;
}
.inner-text-h1 {
  margin-top: 100px;
  margin-left: 100px;
  color: white;
  font-size: 48px;
}
.inner-text-h3 {
  margin-top: 100px;
  margin-left: 100px;
  color: white;
  font-size: 24px;
}

.inner-btn {
  min-width: 300px;
  min-height: 50px;
  position: absolute;
  margin-left: 100px;
  margin-top: 200px;
}
</style>
