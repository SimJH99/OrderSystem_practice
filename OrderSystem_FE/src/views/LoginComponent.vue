<script setup>
import { ref } from 'vue';
import axios from 'axios';
// jwt-decode에서 export시에 중괄호 넣어서 export하였으므로, import할때에도 {} 넣어서 import
import { jwtDecode } from 'jwt-decode';

const email = ref('');
const password = ref(''); 

const doLogin = async () => {
    // 2가지 예외 가능성 :
    // 1) 200번대 상태값이면서 token이 비어있는 경우
    // 2) 200번대 상태값이 아닌 경우
    try{
        const registerData = {
            email: email.value, 
            password: password.value
        };

        const response = await axios.post(`${process.env.VUE_APP_API_BASE_URL}/doLogin`, registerData);
        const token = response.data.result.token;
        console.log(token);
        if(token){
            const decoded = jwtDecode(token);
            console.log(decoded);
            localStorage.setItem("email", decoded.sub);
            localStorage.setItem("Role", decoded.role);
            localStorage.setItem("token", token);
            // created함수는 reload될때 1번만 실행되는 hook함수
            // 그런데, router.push를 통한 화면전환은 reload를 실행시키지 않으므로, created함수 호출이 되지 않음
            window.location.href = "/";
        } else{
            console.log("200 ok but not token");
            alert("Login Failed");
        }
    } catch(error){
        const error_message = error.response.data.error_message;
        if(error_message){
            console.log(error_message);
            alert(error_message);
        } else{
            console.log(error);
            alert("Login Failed")
        } 
    }
}
</script>

<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px">
            <h1>로그인</h1>
        </div>
        <!-- submit은 기본적으로 폼 제출시 브라우저가 페이지를 새로고침하므로 해당 동작을 막기 위해 prevent사용 -->
        <form @submit.prevent="doLogin">
            <div class="form-group">
                <label for="email">이메일</label>
                <input type="text" v-model="email" class="form-control">
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" v-model="password" class="form-control">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary mt-3">로그인</button>
            </div>
        </form>
    </div>
</template>

<style lang="scss" scoped>

</style>