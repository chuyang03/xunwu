# xunwu
基于elasticsearch的搜房网。网站名称寻屋网


笔记：
1.当网站启动的时候寻找WebSecurityConfig这个配置类，在WebSecurityConfig中配置资源访问权限，
比如.failureHandler(authFailHandler()) 配置了登陆验证失败处理器，使得登陆用户或密码错误的时候，根据authFailHandler()这个方法返回一个
登陆验证处理器LoginAuthFailHandler，这个类指定了登陆不成功时显示的信息。
