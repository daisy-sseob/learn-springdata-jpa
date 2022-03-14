package me.sseob.jpa.practice.basic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(
		name = "Member.findByUsername",
		query = "select m from Member as m where m.name = :name"
)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	
	private int age;

	private String name;

	@ManyToOne(fetch = FetchType.LAZY) // 지연 로딩. proxy객체를 조회한다.
	@JoinColumn(name = "team_id")
	private Team team;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<Order> orders = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "locker_id")
	private Locker locker;

	@Embedded
	private Period workPeriod;

	@Embedded
	private Address homeAddress;
	
	/*
	@ElementCollection
	@CollectionTable(name = "FAVORITE_FOOD", joinColumns =
		@JoinColumn(name = "member_id")
	)
	@Column(name = "food_name")
	private Set<String> favoriteFoods = new HashSet<>();
	 */
	
	/*
	collection을 table에 저장할 수 없기 때문에 하위 테이블을 만들어 관리.
	값 type collection들을 Member와 life cycle이 같다. 별로도 persist할 필요없다.
	그러나 !!
	값 타입 컬렉션을 사용하기 보다는 1:N 관계의 Entity를 만들어 그곳에서 Embedded Type을 사용하자.
	실무에서 여러 문제점이 발생한다.
	@ElementCollection
	@CollectionTable(name = "ADDRESS", joinColumns =
		@JoinColumn(name = "member_id")
	) // table명
	private List<Address> addressHistory = new ArrayList<>();
	 */

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private List<AddressEntity> addressHistory = new ArrayList<>();

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "city", column = @Column(name = "work_city")),
			@AttributeOverride(name = "street", column = @Column(name = "work_street")),
			@AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode"))
	}) // embedded type이 겹칠 경우 컬럼명을 재정의 해준다.
	private Address workAddress;

	@Enumerated(EnumType.STRING)
	private MemberType memberType;
	
	public Member(String name) {
		this.name = name;
	}

	public Member() {

	}

	public Address getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(Address workAddress) {
		this.workAddress = workAddress;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}

	public Period getWorkPeriod() {
		return workPeriod;
	}

	public void setWorkPeriod(Period workPeriod) {
		this.workPeriod = workPeriod;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<AddressEntity> getAddressHistory() {
		return addressHistory;
	}

	public void setAddressHistory(List<AddressEntity> addressHistory) {
		this.addressHistory = addressHistory;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public MemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}

	@Override
	public String toString() {
		return "Member{" +
				"id=" + id +
				", age=" + age +
				", name='" + name + '\'' +
				'}';
	}
}
