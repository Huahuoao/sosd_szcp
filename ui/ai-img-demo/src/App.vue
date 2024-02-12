<template>
  <div style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
    <div style="text-align: center;">
      <p>你喜欢什么小动物？</p>
      <input v-model="type">
    </div>
    <div style="text-align: center;">
      <p>你希望它是怎么样的？</p>
      <input v-model="like">
    </div>
    <div style="text-align: center;">
      <p>你不希望它是怎么样的？</p>
      <input v-model="dislike">
    </div>
    <div style="margin-top: 30px;">
      <button @click="sendPostRequest">生成！！</button>
    </div>
    <div style="text-align: center;">
      <h1>{{ text }}</h1>
      <img v-if="imageUrl!=null" :src="imageUrl"
           style="width: 80%; height: 80%; object-fit: contain; aspect-ratio: 1/1;">
    </div>
  </div>
</template>
<script setup>
import {ref} from 'vue';
import axios from "axios";
const text = ref("描述它在你心目中的样子吧！")
const type = ref();
const like = ref();
const dislike = ref();
const imageUrl = ref()
const sendPostRequest = async () => {
  text.value = "正在生成中，需要一点时间哦~"
  try {
    const response = await axios.post('http://sosd.fzuhuahuo.cn:16052/api/v1/image/ai/animal', null, {
      params: {
        type: type.value,
        like: like.value,
        dislike: dislike.value
      }
    });
    console.log(response.status);
    if (response.data.code === 500) {
      text.value = "发生错误了呢，请尝试修改描述词"
    } else {
      text.value = "这是你生成的专属宠物哦"
    }
    imageUrl.value = response.data.data;
  } catch (error) {
    console.error(error);
  }
}
</script>

<style scoped>

</style>
