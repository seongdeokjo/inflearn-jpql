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
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);
            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);
            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);
            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);
            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);
            em.flush();
            em.clear();
//          --------------------------------------------------------------------
            // fetch join
//            String query = "select m from Member m";
//            String query = "select m from Member m join fetch m.team";
//            List<Member> result = em.createQuery(query, Member.class).getResultList();
//            for (Member member : result) {
//                System.out.println("member = " + member.getUsername()+", "+member.getTeam().getName());
//                // 회원1, 팀A(sql) / 회원2, 팀A(1차캐시) / 회원3, 팀B(sql)
//                // 회원 100명 -> N + 1
//            }
//          --------------------------------------------------------------------
            // nullif : 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
//            String query = "select nullif(m.username,'관리자') as username from Member m";
            // coalesce : 하나씩 조회해서 null이 아니면 반환
//            String query = "select coalesce(m.username,'이름 없는 회원') from Member m";
            // 조건식 - case 식
//            String query = "select case when m.age <= 10 then '학생요금'"
//                    +"when m.age >= 60 then '경로요금' else '일반요금' end "
//                    +"from Member m";
//            List<String> resultList = em.createQuery(query, String.class).getResultList();
//            for (String s : resultList) {
//                System.out.println("s = " + s);
//            }
//          --------------------------------------------------------------------
            // jpql 타입 표현
//            String query = "select m.username, 'HELLO', true From Member m "+
//                             "where m.type = :userType";
//            List<Object[]> result = em.createQuery(query)
//                    .setParameter("userType",MemberType.ADMIN)
//                    .getResultList();
//            for (Object[] objects : result) {
//                System.out.println("objects = " + objects[0]);
//                System.out.println("objects = " + objects[1]);
//                System.out.println("objects = " + objects[2]);
//            }
//          ---------------------------------------------------------------------
//            String query = "select m from Member m inner join m.team t";
//            List<Member> result = em.createQuery(query, Member.class)
//                    .getResultList();
//          ----------------------------------------------------------------------
//            for(int i = 0; i < 100; i++){
//                Member member = new Member();
//                member.setUsername("member" + i);
//                member.setAge(i);
//                em.persist(member);
//            }
//            em.flush();
//            em.clear();
//            List<Member> result = em.createQuery("select m from Member m order by m.age", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//            System.out.println("result.size() = " + result.size());
//            for (Member member1 : result) {
//                System.out.println("member1 = " + member1);
//            }
//          -----------------------------------------------------------------------
            // 프로젝션
//            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
//                    .getResultList();
//            MemberDTO memberDTO = result.get(0);
//            System.out.println("memberDTO = " + memberDTO.getUsername());
//            System.out.println("memberDTO = " + memberDTO.getAge());
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