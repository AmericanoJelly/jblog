show tables;
desc user;
desc blog;
desc category;
desc post;

select * from user, blog, category;

select * from category;
select * from blog;
select * from user;
select * from post;

update blog  set title='안녕하세요블로그' where id='22'; 

select no, name, description, blog_id
			 from category
             where blog_id = 'nn'
			 order by no;
             

