const {getList, getDetail, newBlog, updateBlog, delBlog} = require('../controller/blog')
const {SuccessModel, ErrorModel} = require('../model/resModel')

//统一的登录雁阵函数
const  loginCheck = (req) =>{
    if(!req.session.username){
        return Promise.resolve(
            new ErrorModel('尚未登录')
        )
    }

}

const handleBlogRouter = (req, res) =>{
    const method = req.method
    const id = req.query.id

    //获取博客列表
    if(method === 'GET' && req.path === '/api/blog/list'){
        const author = req.query.author || ''
        const keyword = req.query.keyword || ''
        // const listData = getList(author, keyword)
        // return new SuccessModel(listData, '获取博客列表成功')

        const result = getList(author, keyword)
        return result.then((listData) => {
            return new SuccessModel(listData, '获取博客列表成功')
        })
    }

    //获取博客详情
    if(method === 'GET' && req.path === '/api/blog/detail'){
        
        // const data = getDetail(id)

        // console.log(id)
        // return new SuccessModel(data, '获取成功')

        const result = getDetail(id)
        return result.then(data =>{
            return new SuccessModel(data)
        })
    }

    //新建一个博客
    if(method === 'POST' && req.path === '/api/blog/new'){
        // const data = newBlog(req.body)

        // return new SuccessModel(data,'新建博客成功')


        const loginCheckResult = loginCheck(req)
        if(loginCheckResult){
            //未登录
            return loginCheckResult;
        }
        req.body.author=req.session.username
        const result = newBlog(req.body)
        return result.then(data =>{
            return new SuccessModel(data)
        })
    }

    //更新博客
    if(method === 'POST' && req.path === '/api/blog/update'){
        
        const result = updateBlog(id, req.body)
        return result.then(val =>{
            if(val){
                return new SuccessModel(result, '博客更新成功')
            }
            return new ErrorModel(result, '博客更新失败')
        })
        // if(result){
        //     return new SuccessModel(result, '博客更新成功')
        // } else {
        //     return new ErrorModel(result, '博客更新失败')
        // }
        
    }

    //删除博客
    if(method === 'POST' && req.path === '/api/blog/del'){

        req.body.author=req.session.username
        const result = delBlog(id, req.body)
        
        
        return result.then(val => {
            if(val){
                return new SuccessModel(result, '删除成功')
            } else {
                return new ErrorModel(result, '删除失败')
            }
        })
        // if(result){
        //     return new SuccessModel(result, '删除成功')
        // } else {
        //     return new ErrorModel(result, '删除失败')
        // }
    }

}

module.exports = handleBlogRouter