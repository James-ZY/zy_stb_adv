##计算套餐销售量的方法
DELIMITER $$  
DROP FUNCTION IF EXISTS `adv`.`getSellTime` $$  
CREATE FUNCTION `adv`.`getSellTime`(startDate datetime,endDate datetime,queryStartDate datetime,queryEndDate datetime) RETURNS INT 
BEGIN  
## 第一种（比如startDate = 2016-10-01 endDate = 2016-10-31 queryStartDate =
## 2016-09-01 queryEndDate = 2016-12-31)
IF(TIMESTAMPDIFF(DAY,startDate,queryStartDate) <=0
    && TIMESTAMPDIFF(DAY,endDate,queryEndDate) >=0) 
then return (TIMESTAMPDIFF(DAY,startDate,endDate))+1; 
##第二种情况（比如startDate = 2016-09-01 endDate = 2016-11-07
##queryStartDate = 2016-10-01 queryEndDate == 2016-10-31）
ELSEIF (TIMESTAMPDIFF(DAY,startDate,queryStartDate) >=0
    && TIMESTAMPDIFF(DAY,endDate,queryEndDate) <=0)
then return TIMESTAMPDIFF(DAY,queryStartDate,queryEndDate)+1;
##第三种情况（比如startDate = 2016-09-01 endDate = 2016-10-25 queryStartDate =
##2016-10-01 queryEndDate == 2016-12-08）
ELSEIF (TIMESTAMPDIFF(DAY,startDate,queryStartDate) >=0
    && TIMESTAMPDIFF(DAY,endDate,queryEndDate) >=0
    &&  TIMESTAMPDIFF(DAY,endDate,queryStartDate) <=0)
then return TIMESTAMPDIFF(DAY,queryStartDate,endDate)+1;
##第四种情况（比如startDate = 2016-10-01 endDate = 2016-12-25 queryStartDate =
##2016-09-01 queryEndDate == 2016-10-08）
ELSEIF (TIMESTAMPDIFF(DAY,startDate,queryStartDate) <=0
    && TIMESTAMPDIFF(DAY,endDate,queryEndDate) <=0
    &&  TIMESTAMPDIFF(DAY,queryEndDate,startDate) <=0)
then return TIMESTAMPDIFF(DAY,startDate,queryEndDate)+1;
ELSE  RETURN 0;
END IF;  
END $$  
DELIMITER ; 