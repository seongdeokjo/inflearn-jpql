package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);
            em.flush();
            em.clear();
//          -----------------------------------------------------------------------
            // 프로젝션
            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());
//            List<Object[]> resultList = em.createQuery("select distinct m.username, m.age from Member m")
//                    .getResultList();
//            Object[] result = resultList.get(0);
//            System.out.println("username = " + result[0]);
//            System.out.println("age = " + result[1]);
//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
//                    .getResultList();
//          ------------------------------------------------------------------------
            // 파라미터 바인딩 - 이름 기준
//            Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                    .setParameter("username", "member1")
//                    .getSingleResult();
//            System.out.println("result = " + result.getUsername());
//          ------------------------------------------------------------------------
            // 반환 타입이 명확할 때 사용
//            TypedQuery<Member> query = em.createQuery("select m from Member m where m.id = 10", Member.class);
            // 결과가 하나 이상일 때, 리스트 반환 - 결과가 없으면 빈 리스트 반환
//            List<Member> resultList = query.getResultList();
//            for (Member m : resultList) {
//                System.out.println("m = " + m);
//            }
            // 결과가 정확히 하나, 단일 객체 반환 - 결과가 없으면 NoResultException , 둘 이상이면 NonUniqueResultException 예외 발생
//            Member result = query.getSingleResult();
//            System.out.println("result = " + result);
//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            // 반환 타입이 명확하지 않을 때 사용
//            Query query3 = em.createQuery("select m.username,m.age from Member m");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}