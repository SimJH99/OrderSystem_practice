import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from '@/router/index.js';
import '@/assets/css/bootstrap.min.css'
import axios from 'axios';

const app = createApp(App);
const pinia = createPinia();

// 401응답의 경우 interceptor를 통해 공통적으로 토큰 제거 후 로그아웃 처리
axios.interceptors.response.use(response => response, error =>{
    if(error.response && error.response.status === 401){
        localStorage.clear();
        window.location.href = "/login";
    }
    return Promise.reject(error);
})

// vue애플리케이션에서 전역적으로 기능을 사용할 경우에 아래와 같이 use문법 사용
app.use(router);
app.use(pinia);
app.mount('#app');