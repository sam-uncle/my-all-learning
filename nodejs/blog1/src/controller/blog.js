const {exec} = require('../db/mysql')

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
    // console.log("updateBlog blogData....", id, blogData)

    const title = blogData.title
    const content = blogData.content
    
    const sql = `update t_blog set title = '${title}', content='${content}' where id='${id}'`

    return exec(sql).then(updateData => {
        console.log('updateData is ', updateData)
        if(updateData.affectedRows > 0) {
            return true
        }
        return false
    })
}

const delBlog = (id, blogData) => {
    console.log('delBlog ....', id ,blogData)

    const author = blogData.author
    const sql = `delete from t_blog where id = '${id}' and author='${author}'`
    return exec(sql).then(delData => {
        console.log('delData is ', delData)
        if(delData.affectedRows > 0) {
            return true
        }
        return false
    })
}

module.exports={
    getList,
    getDetail,
    newBlog,
    updateBlog,
    delBlog
}