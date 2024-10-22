本文记录了使用双Token机制实现用户认证的具体步骤，前端使用的Vue，后端使用SpringSecurity和JWT



双Token分别指的是AccessToken和RefreshToken

AccessToken：每次请求需要携带AccessToken访问后端数据，有效期短，减少AccessToken泄露带来的风险

RefreshToken：有效期长，只用于AccessToken过期时生成新的AccessToken



使用双Token机制的好处：

无感刷新：使用单个Token时，若Token过期，会强制用户重新登录，影响用户体验。双Token可以实现无感刷新，当AccessToken过期，应用会自动通过RefreshToken生成新的AccessToken，不会打断用户的操作。

提高安全性：若AccessToken有效期很长，当AccessToken被窃取后，攻击者可以长期使用这个Token，因此AccessToken的有效期不易过长。而RefreshToken只用于请求新的AccessToken和RefreshToken，它平时不会直接暴漏在网络中。



双Token认证的基本流程：

1、用户登录后，服务器生成一个短期的访问令牌和一个长期的刷新令牌，并将它们发送给客户端。

2、客户端在每次请求受保护的资源时，携带访问令牌进行身份验证。

3、当访问令牌过期时，客户端使用刷新令牌向服务器请求新的访问令牌。

4、如果刷新令牌有效，服务器生成并返回新的访问令牌；否则，要求用户重新登录。
