<template>
  <v-footer name="footer" inset>
    <v-container>
      <v-row align="center" justify="center">
        <h4>Copyright &copy; 2023-{{ new Date().getFullYear() }} 동동주</h4>
        <v-spacer> </v-spacer>
        <span>주소 : 서울특별시 강남구 테헤란로 212 (멀티캠퍼스 역삼)</span>
        <v-spacer></v-spacer>
        <span>서비스 이용약관</span>
        <v-divider :thickness="2" vertical class="divider"></v-divider>
        <span>개인정보 처리방침</span>
        <v-spacer></v-spacer>
        <v-img
          width="32"
          height="32"
          position="right"
          src="@/assets/logo-1.svg"
        />
      </v-row>
    </v-container>
  </v-footer>
</template>
<style scoped>
.divider {
  margin-left: 20px;
  margin-right: 20px;
}
</style>
