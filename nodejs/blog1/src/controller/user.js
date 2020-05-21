const {exec} = require('../db/mysql')

const login = (postData) =>{
    const username = postData.username
    const password = postData.password
    const sql = `select * from t_user where username = '${username}' and password='${password}'`
    
    return exec(sql).then( rows =>{
        if(rows) {
            return rows[0] || {}
        }
        return false
    })
    
    
    // if(username === 'zhangsan' && password === '123'){
    //     console.log('login check ...')
    //     return true
    // }
    return false
}

module.exports={
    login
}