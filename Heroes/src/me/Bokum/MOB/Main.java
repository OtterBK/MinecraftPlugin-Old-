package me.Bokum.MOB;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.AtomEffect;
import de.slikey.effectlib.effect.BigBangEffect;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.DiscoBallEffect;
import de.slikey.effectlib.effect.EarthEffect;
import de.slikey.effectlib.effect.ExplodeEffect;
import de.slikey.effectlib.effect.FountainEffect;
import de.slikey.effectlib.effect.GridEffect;
import de.slikey.effectlib.effect.HeartEffect;
import de.slikey.effectlib.effect.HelixEffect;
import de.slikey.effectlib.effect.HillEffect;
import de.slikey.effectlib.effect.IconEffect;
import de.slikey.effectlib.effect.JumpEffect;
import de.slikey.effectlib.effect.LoveEffect;
import de.slikey.effectlib.effect.MusicEffect;
import de.slikey.effectlib.effect.SkyRocketEffect;
import de.slikey.effectlib.effect.SmokeEffect;
import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.effect.TornadoEffect;
import de.slikey.effectlib.effect.TraceEffect;
import de.slikey.effectlib.effect.VortexEffect;
import de.slikey.effectlib.effect.WaveEffect;
import me.Bokum.MOB.Ability.Ability;
import me.Bokum.MOB.Attacker.Assasin;
import me.Bokum.MOB.Attacker.BowMaster;
import me.Bokum.MOB.Attacker.FlameWizard;
import me.Bokum.MOB.Attacker.Ninja;
import me.Bokum.MOB.Attacker.PassiveMaster;
import me.Bokum.MOB.Expert.Bomber;
import me.Bokum.MOB.Expert.Engineer;
import me.Bokum.MOB.Expert.Randomer;
import me.Bokum.MOB.Expert.Tracer;
import me.Bokum.MOB.Game.Cooldown;
import me.Bokum.MOB.Game.MobSystem;
import me.Bokum.MOB.Tanker.Blocker;
import me.Bokum.MOB.Tanker.Imrapu;
import me.Bokum.MOB.Tanker.Knight;
import me.Bokum.MOB.Tanker.Shielder;
import me.Bokum.Supporter.AreaCreator;
import me.Bokum.Supporter.Portal;
import me.Bokum.Supporter.Priest;
import me.Bokum.Supporter.Witch;

public class Main extends JavaPlugin implements Listener{
	public static String MS = "§f[ §a뽀끔 §f] ";
	public static Main instance;
    public static EffectManager effectManager;
    
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
        
        EffectLib lib = EffectLib.instance();
        effectManager = new EffectManager(lib);
        
		instance = this;
		getLogger().info("히어로즈 플러그인이 로드 되었습니다.");
	}
    
	public void onDisable(){
		getLogger().info("히어로즈 플러그인이 로드 되었습니다.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args){
		if(talker instanceof Player){
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("mob") && p.isOp()){
				if(args.length <= 0){
					HelpMessages(p);
				}
				else{
					Execute(p, args);
				}
			}
		}
		return true;
	}
	
	public void HelpMessages(Player p){
		p.sendMessage(MS+"/mob test - 게임참여");
	}
	
	public void Execute(Player p, String[] args){
		if(args[0].equalsIgnoreCase("test")){
			switch(Integer.valueOf(args[1])){
			case -4: Cooldown.cooldownlist.clear(); break;
			case -3: MobSystem.RemoveAbility(p); break;
			case 0: MobSystem.ablist.get(p.getName()).description(); break;
			case 1: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new BowMaster(p.getName(), p)); break;
			case 2: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Tracer(p.getName(), p)); break;
			case 3: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Ninja(p.getName(), p)); break;
			case 4: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Assasin(p.getName(), p)); break;
			case 5: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Shielder(p.getName(), p)); break;
			case 6: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Engineer(p.getName(), p)); break;
			case 7: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Portal(p.getName(), p)); break;
			case 8: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Witch(p.getName(), p)); break;
			case 9: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new PassiveMaster(p.getName(), p)); break;
			case 10: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Bomber(p.getName(), p)); break;
			case 11: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Randomer(p.getName(), p)); break;
			case 12: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Knight(p.getName(), p)); break;
			case 13: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new AreaCreator(p.getName(), p)); break;
			case 14: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Blocker(p.getName(), p)); break;
			case 15: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Imrapu(p.getName(), p)); break;
			case 16: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new Priest(p.getName(), p)); break;
			case 17: MobSystem.RemoveAbility(p); MobSystem.ablist.put(p.getName(), new FlameWizard(p.getName(), p)); break;
			case 18: AnimatedBallEffect ef = new AnimatedBallEffect(Main.effectManager);
			ef.setTargetEntity(p); ef.setEntity(p); ef.start(); return;
			case 19: AtomEffect ef2 = new AtomEffect(Main.effectManager);
			ef2.setTargetEntity(p); ef2.setEntity(p); ef2.start(); return;
			case 20: BigBangEffect ef3 = new BigBangEffect(Main.effectManager);
			ef3.setTargetEntity(p); ef3.setEntity(p); ef3.start(); return;
			case 21: EarthEffect ef4 = new EarthEffect(Main.effectManager);
			ef4.setTargetEntity(p); ef4.setEntity(p); ef4.start(); return;
			case 22: ConeEffect ef5 = new ConeEffect(Main.effectManager);
			ef5.setTargetEntity(p); ef5.setEntity(p); ef5.start(); return;
			case 23: DiscoBallEffect ef6 = new DiscoBallEffect(Main.effectManager);
			ef6.setTargetEntity(p); ef6.setEntity(p); ef6.start(); return;
			case 24: ExplodeEffect ef7 = new ExplodeEffect(Main.effectManager);
			ef7.setTargetEntity(p); ef7.setEntity(p); ef7.start(); return;
			case 25: FountainEffect ef8 = new FountainEffect(Main.effectManager);
			ef8.setTargetEntity(p); ef8.setEntity(p); ef8.start(); return;
			case 26: GridEffect ef9 = new GridEffect(Main.effectManager);
			ef9.setTargetEntity(p); ef9.setEntity(p); ef9.start(); return;
			case 27: HeartEffect ef10 = new HeartEffect(Main.effectManager);
			ef10.setTargetEntity(p); ef10.setEntity(p); ef10.start(); return;
			case 28: HelixEffect ef11 = new HelixEffect(Main.effectManager);
			ef11.setTargetEntity(p); ef11.setEntity(p); ef11.start(); return;
			case 29: HillEffect ef12 = new HillEffect(Main.effectManager);
			ef12.setTargetEntity(p); ef12.setEntity(p); ef12.start(); return;
			case 30: IconEffect ef13 = new IconEffect(Main.effectManager);
			ef13.setTargetEntity(p); ef13.setEntity(p); ef13.start(); return;
			case 31: JumpEffect ef14 = new JumpEffect(Main.effectManager);
			ef14.setTargetEntity(p); ef14.setEntity(p); ef14.start(); return;
			case 32: LoveEffect ef15 = new LoveEffect(Main.effectManager);
			ef15.setTargetEntity(p); ef15.setEntity(p); ef15.start(); return;
			case 33: MusicEffect ef16 = new MusicEffect(Main.effectManager);
			ef16.setTargetEntity(p); ef16.setEntity(p); ef16.start(); return;
			case 34: SkyRocketEffect ef17 = new SkyRocketEffect(Main.effectManager);
			ef17.setTargetEntity(p); ef17.setEntity(p); ef17.start(); return;
			case 35: SmokeEffect ef18 = new SmokeEffect(Main.effectManager);
			ef18.setTargetEntity(p); ef18.setEntity(p); ef18.start(); return;
			case 36: TextEffect ef19 = new TextEffect(Main.effectManager);
			ef19.setTargetEntity(p); ef19.setEntity(p); ef19.start(); return;
			case 37: TornadoEffect ef20 = new TornadoEffect(Main.effectManager);
			ef20.setTargetEntity(p); ef20.setEntity(p); ef20.start(); return;
			case 38: TraceEffect ef21 = new TraceEffect(Main.effectManager);
			ef21.setTargetEntity(p); ef21.setEntity(p); ef21.start(); return;
			case 39: VortexEffect ef22 = new VortexEffect(Main.effectManager);
			ef22.setTargetEntity(p); ef22.setEntity(p); ef22.start(); return;
			case 40: WaveEffect ef23 = new WaveEffect(Main.effectManager);
			ef23.setTargetEntity(p); ef23.setEntity(p); ef23.start(); return;
			
			default: return;
			}
			MobSystem.ablist.get(p.getName()).can_Ultimate = true;
			MobSystem.ablist.get(p.getName()).can_passive = true;
		}
	}
	
	@EventHandler
	public void PlayerItemHeld(PlayerItemHeldEvent e){
		if(MobSystem.ablist.containsKey(e.getPlayer().getName())){
			Ability ability = MobSystem.ablist.get(e.getPlayer().getName());
			if(MobSystem.skill_block){
				e.getPlayer().sendMessage(Main.MS+"대기중에는 스킬을 사용하실 수 없습니다.");
				return;
			}
			if(MobSystem.ablist.containsKey(e.getPlayer().getName())){
				if(ability.check_dead){
					e.getPlayer().sendMessage(Main.MS+"부활 대기중에는 스킬을 사용하실 수 없습니다.");
					return;	
				}
			}
			if(ability.cc){
				e.getPlayer().sendMessage(Main.MS+"침묵 상태에서는 스킬을 사용하실 수 없습니다.");
				e.getPlayer().getInventory().setHeldItemSlot(0);
				return;
			}else{
				ability.Active(e);
			}
		}
	}
	
	  @EventHandler
	  public void onQuitPlayer(PlayerQuitEvent e) {
	    if (MobSystem.plist.contains(e.getPlayer()))
	    {
	      MobSystem.GameQuit(e.getPlayer());
	    }
	  }
	
	@EventHandler
	public void Passive(BlockBreakEvent e){
		Player p = e.getPlayer();
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onBlockBreak(e);
		}
	}
	
	@EventHandler
	public void Passive(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onDeath(e);
		}
		if(p.getKiller() instanceof Player){
			Player k = (Player) p.getKiller();
			if(MobSystem.ablist.containsKey(k.getName())){
				Ability ability2 = MobSystem.ablist.get(k.getName());
				ability2.onKill(e);
			}
		}
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		Entity ent = event.getEntity();
		if (ent instanceof Fireball) {
			Fireball fb = (Fireball) event.getEntity();
			if(fb.getShooter() != null && fb.getShooter() instanceof Player){
				Player p = (Player) fb.getShooter();
				if(MobSystem.ablist.containsKey(p.getName())){
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent event) {
		Entity ent = event.getEntity();
		if (ent instanceof Fireball) {
			Fireball fb = (Fireball) event.getEntity();
			if(fb.getShooter() != null && fb.getShooter() instanceof Player){
				Player p = (Player) fb.getShooter();
				if(MobSystem.ablist.containsKey(p.getName())){
					event.setFire(false);
					event.setRadius(2);
				}
			}
		}		
	}
	
	/*@EventHandler
	public void Passive(EntityRegainHealthEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		if(!MobSystem.plist.contains((Player)e.getEntity())) return;
		if(e.getRegainReason() == RegainReason.REGEN || e.getRegainReason() == RegainReason.SATIATED)
		e.setCancelled(true);
	}*/
	
	@EventHandler
	public void Passive(EntityDamageByEntityEvent e){
		Entity entity = e.getEntity();
		Player d = null;
		if(e.getDamager() instanceof Arrow){
			Arrow arrow = (Arrow) e.getDamager();
			d = (Player) arrow.getShooter();
			if(!MobSystem.ablist.containsKey(d.getName())) return;
			Ability d_ablity = MobSystem.ablist.get(d.getName());
			if(d_ablity.abilityName.equalsIgnoreCase("보우 마스터")) d_ablity.onArrowHit(entity);
		}
		if(e.getDamager() instanceof Fireball){
			Fireball fireball = (Fireball) e.getDamager();
			if(fireball.getShooter() instanceof Player){
				d = (Player) fireball.getShooter();
			}
		}
		if(e.getDamager() instanceof Player){
			d = (Player) e.getDamager();
		}
		if(d == null) return;
		if(entity instanceof Player) {
			Player p = (Player) entity;
			if(!MobSystem.ablist.containsKey(p.getName())) return;
			Ability p_ability = MobSystem.ablist.get(p.getName());
			p_ability.onHitDamaged(e);
		}
		if(!MobSystem.ablist.containsKey(d.getName())) return;
		Ability d_ability = MobSystem.ablist.get(d.getName());
		if(d_ability.ignore){
			d_ability.ignore = false;
		}else{
			d_ability.onHit(e);
		}
	}
	
	@EventHandler
	public void Passive(EntityDamageEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onDamaged(e);
		}
	}
	
	@EventHandler
	public void Passive(BlockPlaceEvent e){
		Player p = e.getPlayer();
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onBlockPlace(e);
		}
	}
	
	@EventHandler
	public void Passive(PlayerMoveEvent e){
		if(e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY()
				&& e.getFrom().getBlockZ() == e.getTo().getBlockZ()) return;
		final Player p = e.getPlayer();
		final Location l = new Location(e.getTo().getWorld(), e.getTo().getBlockX(), e.getTo().getBlockY(), e.getTo().getBlockZ());
		if(MobSystem.portalloc.containsKey(l)){
			Location l1 = MobSystem.portalloc.get(l);
			Location l2 = new Location(l1.getWorld(), l1.getBlockX(), l1.getBlockY(), l1.getBlockZ());
			l2.setPitch(p.getLocation().getPitch());
			l2.setYaw(p.getLocation().getYaw());
			p.teleport(l2);
			p.getWorld().playSound(l, Sound.ENDERMAN_TELEPORT, 2.0f, 1.0f);
			p.getWorld().playSound(l2, Sound.ENDERMAN_TELEPORT, 2.0f, 1.0f);
		}
	}

	@EventHandler
	public void Passive(ProjectileLaunchEvent e){
		if(e.getEntity().getShooter() == null || !(e.getEntity().getShooter() instanceof Player)) return;
		Player p = (Player) e.getEntity().getShooter();
		if(!MobSystem.plist.contains(p)) return;
		if(MobSystem.ablist.containsKey(p.getName())){
			Ability ability = MobSystem.ablist.get(p.getName());
			ability.onLaunch(e);
		}
	}
}
