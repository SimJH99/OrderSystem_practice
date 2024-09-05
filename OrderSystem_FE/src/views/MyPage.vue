<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios'
import OrderListComponenet from '@/components/OrderListComponenet.vue';

const member = ref([]);

onMounted (async () => {
    try {
        const token = localStorage.getItem('token')
        const headers =  {Authorization : `Bearer ${token}`};
        const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/member/myInfo`, {headers});
        member.value = response.data;
    } catch(error){
        console.log(error)
    }
})
</script>

<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>회원 목록</h1></div>
        <table class="table">
                <tr>
                    <td>이름 : </td><td>{{member.name}}</td>
                </tr>
                <tr>
                    <td>email : </td><td>{{member.email}}</td>
                </tr>
                <tr>
                    <td>도시 : </td><td>{{member.city}}</td>
                </tr>
                <tr>
                    <td>상세주소 : </td><td>{{member.street}}</td>
                </tr>
                <tr>
                    <td>우편정보 : </td><td>{{member.zipcode}}</td>
                </tr>
        </table>
    </div>
    <OrderListComponenet :isAdmin="false" apiUrl="/member/myorders"/>
</template>