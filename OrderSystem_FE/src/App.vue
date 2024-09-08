<script setup>
// 절대경로 : @는 jsconfig의 경로 키워드로 선언
import HeaderComponent from "@/components/HeaderComponent.vue"
import FooterComponent from "@/components/FooterComponent.vue";
</script>

<template>
  <HeaderComponent/>
  <div>
    <router-view/>
  </div>
  <FooterComponent/>
</template>

<style>
</style>


