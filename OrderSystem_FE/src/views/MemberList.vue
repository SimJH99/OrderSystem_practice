<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios'

const memberList = ref([]);

onMounted(async () => {
    try{
        const token = localStorage.getItem('token')
        const headers =  {Authorization : `Bearer ${token}`};
        const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/members`, {headers});
        memberList.value = response.data;
    } catch(error){
        console.log(error)
    }
})
</script>

<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>회원 목록</h1></div>
        <table class="table">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>주문수량</th>
                </tr>
            </thead>
            <tbody>
            <tr v-for="member in memberList" :key="member.id">
                <td>{{member.id}}</td>
                <td>{{member.name}}</td>
                <td>{{member.email}}</td>
                <td>
                    <a :href="`/member/${member.id}/orders`">{{member.orderCount}}</a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<style lang="scss" scoped>

</style>