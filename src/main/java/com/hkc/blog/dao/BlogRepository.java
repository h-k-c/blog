package com.hkc.blog.dao;

import com.hkc.blog.po.Blog;
import com.sun.xml.bind.v2.schemagen.episode.Package;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author 胡开成
 * @Date 2020/3/3 15:17
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend=true ")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Modifying
    @Query("update  Blog b set b.views=b.views+1 where b.id=?1")
    int updateViews(Long id);

    //等同于  SELECT DATE_FORMAT(b.update_time,'%Y') as year FROM t_blog b GROUP BY year ORDER BY year DESC;
    /*@Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc")*/
    @Query(value = "SELECT DATE_FORMAT(b.update_time,'%Y') as year FROM t_blog b GROUP BY year ORDER BY year DESC",nativeQuery = true)
    List<String> findGroupYear();

    //SELECT * FROM t_blog b WHERE DATE_FORMAT(b.update_time,'%Y') = '2020'
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
