const base = {
    get() {
        return {
            url : "http://localhost:8080/jiaoxuefuzhuxitong/",
            name: "jiaoxuefuzhuxitong",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/jiaoxuefuzhuxitong/front/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "教学辅助系统"
        } 
    }
}
export default base
