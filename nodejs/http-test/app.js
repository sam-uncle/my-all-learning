const http = require('http')
const querystring = require('querystring')

// const server = http.createServer((req,res) => {
//     console.log('method:', req.method)

//     const url = req.url
//     console.log(url)

//     req.query = querystring.parse(url.split('?')[1])
//     console.log('query:',req.query)
//     res.end(JSON.stringify(req.query))

// })

const server = http.createServer((req, res) =>{
    // if(req.method === 'POST'){
    //     console.log("content-type", req.headers['content-type'])
    //     //接收数据
    //     let postDate = '';
    //     req.on('data', chunk =>{
    //         postDate += chunk.toString()
    //     })
    //     req.on('end',()=>{
    //         console.log('postDate:', postDate)
    //         res.end('hello world')
    //     })

    // }
    const method = req.method
    console.log(method)
    const url=req.url
    const path=url.split('?')[0]
    const query = querystring.parse(url.split('?')[1])
    //设置返回格式为json
    res.setHeader['Content-type', 'application/json'] //text/html

    //定义返回数据
    const resDate = {
        method,
        url,
        path,
        query
    }

    //返回
    if(method === 'GET'){
        res.end(
            JSON.stringify(resDate)
        )
    }

    if(method === 'POST'){
        let postDate = ''

        req.on('data', chunk=>{
            postDate += chunk.toString()
        })

        req.on('end',()=>{
            resDate.postDate = postDate
            res.end(
                JSON.stringify(resDate)
            )
        })
        
    }

})
server.listen(8000, () =>{
    console.log('server listening to 8000')
})