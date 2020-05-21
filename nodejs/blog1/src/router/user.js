const {login} = require('../controller/user')
const { SuccessModel, ErrorModel } = require('../model/resModel')

const {set} = require('../db/redis')
//获取cookie的过期时间
const getCookieExpires = ()=>{
    const d = new Date()
    d.setTime(d.getTime() + (24*60*60*1000))
    console.log(d.toGMTString())
    return d.toGMTString()
}


const handleUserRouter = (req, res) =>{
    const method = req.method
    const url = req.url
    const path = url.split('?')[0]

    //登录
    if(method === 'GET' && req.path === '/api/user/login'){

        const result = login(req.query)

        return result.then(data => {
            if(data.username){
                //操作cookie
                req.session.username = data.username
                console.log('req.session.username == ', req.session.username)
                //同步redis
                set(req.sessionId, req.session)
                return new SuccessModel('欢迎'+data.username)
            } else {
    
                return new ErrorModel('登录失败')
            }
        })
        
    }

    if(method === 'GET' && req.path === '/api/user/login-test'){
        if(req.cookie.username){
            return Promise.resolve(new SuccessModel())
        }

        return Promise.resolve(new ErrorModel('尚未登录'))
    }

}

module.exports=handleUserRouter