const loginCheck = (postData) =>{
    const username = postData.username
    const password = postData.password
    if(username === 'zhangsan' && password === '123'){
        console.log('login check ...')
        return true
    }
    return false
}

module.exports={
    loginCheck
}