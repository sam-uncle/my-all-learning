const handlerBlogRouter = require('./src/router/blog')
const handlerUserRouter = require('./src/router/user')
const querystring = require('querystring')

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
    // const resData = {
    //     name :'双越100',
    //     site :'imooc',
    //     env : process.env.NODE_ENV
    // }

    // res.end(
    //     JSON.stringify(resData)
    // )

    getPostData(req, res).then(postData => {
        req.body = postData

        
        //处理blog路由
        const blogDate = handlerBlogRouter(req, res);
        if (blogDate) {
            res.end(
                JSON.stringify(blogDate)
            )
            return
        }

        //处理用户路由
        const userDate = handlerUserRouter(req, res);
        if (userDate) {
            res.end(
                JSON.stringify(userDate)
            )
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