<script setup>
import axios from 'axios';
import { defineProps, ref, onMounted } from 'vue';

const props = defineProps({
    isAdmin: Boolean,
    apiUrl: String
})

const orderList = ref([]);
const visibleOrder = ref(new Set());

onMounted(async () => {
    try{
        const token = localStorage.getItem('token');
        const headers = token ? {Authorization : `Bearer ${token}`} : {};
        const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}${props.apiUrl}`, {headers});
        orderList.value = response.data;
    } catch(error){
        console.log(error);
    }
})

const toggleOrder = ((orderId) => {
    if(visibleOrder.value.has(orderId)){
            visibleOrder.value.delete(orderId);
    }else{
        visibleOrder.value.add(orderId);
    }
})

const cancelOrder = (async (orderId) => {
    if(confirm("정말 삭제 하시겠습니까")){
        try{
            const token = localStorage.getItem('token');
            const headers =  {Authorization : `Bearer ${token}`};
            await axios.delete(`${process.env.VUE_APP_API_BASE_URL}/${orderId}/cancel`, {headers});
            const order = this.orderList.find(order => order.id === orderId);
            order.orderStatus = "CANCELED";
        } catch(error){
            console.log(error);
            alert("주문취소에 실패했습니다.");
        }
    }      
})
</script>
<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>주문 목록</h1></div>
        <table class="table">
                <thead>
                    <th>#</th>
                    <th>회원Email</th>
                    <th>주문상태</th>
                    <th v-if="props.isAdmin === true">ACTION</th>
                </thead>
                <template v-for="order in orderList" :key="order.id">
                    <tr @click="toggleOrder(order.id)" style="cursor: pointer">
                        <td>{{order.id}}</td>
                        <td>{{order.memberEmail}}</td>
                        <td>{{order.orderStatus}}</td>
                        <td v-if="props.isAdmin === true"><button v-if="order.orderStatus==='ORDERED'" @click.stop="cancelOrder(order.id)">CANCEL</button></td>
                    </tr>
                    <tr v-if="visibleOrder.has(order.id)">
                        <td colspan="4">
                            <span v-for="orderItem in order.orderItems" :key="orderItem.id">
                                {{ orderItem.itemName }} {{ orderItem.count }}개
                            </span>
                        </td>
                    </tr>
                </template>
        </table>
    </div>
</template>