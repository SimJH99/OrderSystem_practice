<script setup>
import { ref } from 'vue';
import router from '@/router';
import axios from 'axios';

const name = ref('');
const email = ref('');
const password = ref('');
const city = ref('');
const street = ref('');
const zipcode = ref('');
const role = ref('');

const memberCreate = async () => {
    try{
        const registerData = {
            name: name.value ,
            email: email.value,
            password: password.value,
            city: city.value,
            street: street.value,
            zipcode: zipcode.value,
            role: role.value
        };
        await axios.post(`${process.env.VUE_APP_API_BASE_URL}/member/create`, registerData);
        alert("회원가입 완료");
        router.push({name : 'LoginComponent'});
    } catch(error){
        console.log(error);
    }
}
</script>

<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px">
            <h1>회원가입</h1>
        </div>
        <form @submit.prevent="memberCreate">
            <div class="form-group">
                <label>이름</label>
                <input type="text" class="form-control" v-model="name">
            </div>
            <div class="form-group">
                <label>이메일</label>
                <input type="text" class="form-control" v-model="email">
            </div>
            <div class="form-group">
                <label>비밀번호</label>
                <input type="password" class="form-control" v-model="password">
            </div>
            <div class="form-group">
                <label>도시</label>
                <input type="text" class="form-control" v-model="city">
            </div>
            <div class="form-group">
                <label>상세주소</label>
                <input type="text" class="form-control" v-model="street">
            </div>
            <div class="form-group">
                <label>우편번호</label>
                <input type="text" class="form-control" v-model="zipcode">
            </div>
            <div class="form-group">
                <label>권한</label>
                <select name="role" class="form-control" v-model="role">
                    <option>==선택==</option>
                    <option value="ADMIN">관리자</option>
                    <option value="USER">회원</option>
                </select>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary mt-3">가입하기</button>
            </div>
            <div th:if="${errorMessage != null}">
                <input type="hidden" id="error" th:value="${errorMessage}">
            </div>
        </form>
    </div>
</template>