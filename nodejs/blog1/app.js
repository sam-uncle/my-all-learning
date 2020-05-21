const handlerBlogRouter = require('./src/router/blog')
const handlerUserRouter = require('./src/router/user')
const querystring = require('querystring')
const {set,get} = require('./src/db/redis')
// //session数据
// const SESSION_DATA = {}

const getPostData = (req, res) => {
    const promise = new Promise((resolve, reject) => {
        if (req.method !== 'POST') {
            resolve({})
            return
        }

        if (req.headers['content-type'] !== 'application/json') {
            resolve({})
            return
        }

        let postData = ''
        req.on('data', chunk => {
            postData += chunk.toString()
        })

        req.on('end', () => {
            if (!postData) {
                resolve({})
                return
            }
            resolve(JSON.parse(postData))
        })
    })
    return promise
}

const serverHandler = (req, res) => {
    //设置返回格式json
    res.setHeader('Content-type', 'application/json');
    const url = req.url
    req.path = url.split('?')[0]


    //结息query
    req.query = querystring.parse(url.split('?')[1])

    req.cookie = {}
    //解析 cookie
    const cookieStr = req.headers.cookie || ''; //key = value
    cookieStr.split(';').forEach(element => {
        if(!element){
            return
        }
        const arr = element.split('=')
        const key = arr[0].trim()
        const val = arr[1].trim()
        req.cookie[key] = val
    });
    console.log('req.cookie == ', req.cookie)


    // //解析session
    // let needSetCookie = false
    // let userId = req.cookie.userId;
    // if(userId){
    //     if(!SESSION_DATA[userId]){
    //         SESSION_DATA[userId] = {}
    //     }
    // } else {
    //     needSetCookie = true
    //     userId = `${Date.now()}_${Math.random()}`
    //     SESSION_DATA[userId] = {}
    // }
    // req.sesion = SESSION_DATA[userId]
    // const resData = {
    //     name :'双越100',
    //     site :'imooc',
    //     env : process.env.NODE_ENV
    // }

    // res.end(
    //     JSON.stringify(resData)
    // )


    //解析session（使用redis）
    let needSetCookie = false
    let userId = req.cookie.userId
    if(!userId){
        needSetCookie = true
        userId = `${Date.now()}_${Math.random()}`
        //初始化session
        set(userId, {})

    }
    //获取session
    req.sessionId = userId
    get(req.sessionId).then(sessionData =>{
        if(sessionData == null){
            //初始化session 中的session值
            set(req.sessionId, {})
        
            //设置session
            req.session = {}

        } else {
            //设置session
            req.session = sessionData
        }
        console.log('req.session ==', req.session)
        //处理post data
        return getPostData(req)
    }).then(postData => {
        req.body = postData

        
        //处理blog路由
        const blogResult = handlerBlogRouter(req, res);
        if(blogResult){
            blogResult.then((blogDate)=>{
                if (blogDate) {
                    res.end(
                        JSON.stringify(blogDate)
                    )
                }
            })
            return
        }

        //处理用户路由
        const userResult = handlerUserRouter(req, res);
        if(userResult){
            userResult.then(userData =>{
                if (userData) {
                    if(needSetCookie){
                        res.setHeader('Set-Cookie', `userId=${userId}; path=/; httpOnly`)
                    }
                    res.end(
                        JSON.stringify(userData)
                    )
                }
               
            })
            return
        }
        
        //未命中路由，返回 404
        res.writeHead(404, {
            "Content-type": "text/plain"
        })
        res.write("404 Not Found \n")
        res.end()
    })


}

module.exports = serverHandler;