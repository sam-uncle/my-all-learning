const getList = (author, keyword) =>{
    //先返回假数据（格式是正确的）
    return [
        {
            id : 1,
            title:'标题a',
            content : '内容a',
            createTime:new Date(),
            author:'张三'
        },
        {
            id : 2,
            title:'标题b',
            content : '内容b',
            createTime:new Date(),
            author:'李四'
        }
    ]
}

const getDetail = (id) =>{
    //先返回假数据（格式是正确的）
    return {
        id : 1,
        title:'标题a',
        content : '内容a',
        createTime:new Date(),
        author:'张三'
    }
}

//新建博客
const newBlog = (blogData = {}) =>{
    //blogData 是一個對象，包含titile context
    console.log("newBlog blogData....", blogData)
    return {
        id:3//表示新建博客插入表里面的id
    }
}


//更新博客
const updateBlog = (id, blogData = {}) =>{
    //blogData 是一個對象，包含titile context
    console.log("updateBlog blogData....", id, blogData)
    return true
}

const delBlog = (id, blogData) => {
    console.log('delBlog ....', id ,blogData)
    return true
}

module.exports={
    getList,
    getDetail,
    newBlog,
    updateBlog,
    delBlog
}