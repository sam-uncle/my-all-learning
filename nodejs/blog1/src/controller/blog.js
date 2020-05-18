const {exec} = require('../db/mysql')
const mysql = require('../db/mysql')

const getList = (author, keyword) =>{
    let sql = 'select * from t_blog where 1 = 1'


    if(author){
        sql += ` and author='${author}'`
    }

    if(keyword){
        sql += ` and title like '%${keyword}%'`
    }

    sql += ` order by create_time desc`

    //返回promise
    return exec(sql)
}

const getDetail = (id) =>{
    let sql = `select * from t_blog where id = '${id}'`
    return exec(sql).then(rows =>{
        return rows[0]
    })
}

//新建博客
const newBlog = (blogData = {}) =>{
    // //blogData 是一個對象，包含titile context
    // console.log("newBlog blogData....", blogData)
    // return {
    //     id:3//表示新建博客插入表里面的id
    // }

    const title = blogData.title
    const content = blogData.content
    const author = blogData.author
    const createTime = Date.now()

    let sql = `insert into t_blog(title, content, author, create_time) values('${title}','${content}','${author}','${createTime}')`

    return exec(sql).then( insertDate =>{
        console.log('insertData', insertDate)
        return {
            id: insertDate.insertId
        }
    })


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