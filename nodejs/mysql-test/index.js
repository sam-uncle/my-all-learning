const mysql = require('mysql')

//创建连接对象
const con = mysql.createConnection({
    host:"localhost",
    port:"3306",
    user:"root",
    password:"root",
    database:"myblog"
})

//开始连接
con.connect()

//执行sql语句
const sql = 'select * from t_user'
con.query(sql, (err, result) => {
    if(err){
        console.error(err)
    }

    console.log(result)
})

//关闭连接
con.end()