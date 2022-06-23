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

select * from post where category_no ='19';

select no, title, contents, category_no from post where category_no=15;


select no, title, contents, category_no 
			from post 
			where category_no=15 and no= 2;
select no, title, contents, category_no 
			from post 
			where category_no=15 order by no limit 0,1;
            
select no, title, contents, category_no 
from post 
where category_no order by no limit 0, 1;

