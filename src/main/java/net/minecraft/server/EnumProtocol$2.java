package net.minecraft.server;



















 enum EnumProtocol$2
{
  EnumProtocol$2(int paramInt1)
  {
    super(paramString, paramInt, paramInt1, null);
    b(0, PacketPlayOutKeepAlive.class);
    b(1, PacketPlayOutLogin.class);
    b(2, PacketPlayOutChat.class);
    b(3, PacketPlayOutUpdateTime.class);
    b(4, PacketPlayOutEntityEquipment.class);
    b(5, PacketPlayOutSpawnPosition.class);
    b(6, PacketPlayOutUpdateHealth.class);
    b(7, PacketPlayOutRespawn.class);
    b(8, PacketPlayOutPosition.class);
    b(9, PacketPlayOutHeldItemSlot.class);
    b(10, PacketPlayOutBed.class);
    b(11, PacketPlayOutAnimation.class);
    b(12, PacketPlayOutNamedEntitySpawn.class);
    b(13, PacketPlayOutCollect.class);
    b(14, PacketPlayOutSpawnEntity.class);
    b(15, PacketPlayOutSpawnEntityLiving.class);
    b(16, PacketPlayOutSpawnEntityPainting.class);
    b(17, PacketPlayOutSpawnEntityExperienceOrb.class);
    b(18, PacketPlayOutEntityVelocity.class);
    b(19, PacketPlayOutEntityDestroy.class);
    b(20, PacketPlayOutEntity.class);
    b(21, PacketPlayOutRelEntityMove.class);
    b(22, PacketPlayOutEntityLook.class);
    b(23, PacketPlayOutRelEntityMoveLook.class);
    b(24, PacketPlayOutEntityTeleport.class);
    b(25, PacketPlayOutEntityHeadRotation.class);
    b(26, PacketPlayOutEntityStatus.class);
    b(27, PacketPlayOutAttachEntity.class);
    b(28, PacketPlayOutEntityMetadata.class);
    b(29, PacketPlayOutEntityEffect.class);
    b(30, PacketPlayOutRemoveEntityEffect.class);
    b(31, PacketPlayOutExperience.class);
    b(32, PacketPlayOutUpdateAttributes.class);
    b(33, PacketPlayOutMapChunk.class);
    b(34, PacketPlayOutMultiBlockChange.class);
    b(35, PacketPlayOutBlockChange.class);
    b(36, PacketPlayOutBlockAction.class);
    b(37, PacketPlayOutBlockBreakAnimation.class);
    b(38, PacketPlayOutMapChunkBulk.class);
    b(39, PacketPlayOutExplosion.class);
    b(40, PacketPlayOutWorldEvent.class);
    b(41, PacketPlayOutNamedSoundEffect.class);
    b(42, PacketPlayOutWorldParticles.class);
    b(43, PacketPlayOutGameStateChange.class);
    b(44, PacketPlayOutSpawnEntityWeather.class);
    b(45, PacketPlayOutOpenWindow.class);
    b(46, PacketPlayOutCloseWindow.class);
    b(47, PacketPlayOutSetSlot.class);
    b(48, PacketPlayOutWindowItems.class);
    b(49, PacketPlayOutWindowData.class);
    b(50, PacketPlayOutTransaction.class);
    b(51, PacketPlayOutUpdateSign.class);
    b(52, PacketPlayOutMap.class);
    b(53, PacketPlayOutTileEntityData.class);
    b(54, PacketPlayOutOpenSignEditor.class);
    b(55, PacketPlayOutStatistic.class);
    b(56, PacketPlayOutPlayerInfo.class);
    b(57, PacketPlayOutAbilities.class);
    b(58, PacketPlayOutTabComplete.class);
    b(59, PacketPlayOutScoreboardObjective.class);
    b(60, PacketPlayOutScoreboardScore.class);
    b(61, PacketPlayOutScoreboardDisplayObjective.class);
    b(62, PacketPlayOutScoreboardTeam.class);
    b(63, PacketPlayOutCustomPayload.class);
    b(64, PacketPlayOutKickDisconnect.class);
    
    a(0, PacketPlayInKeepAlive.class);
    a(1, PacketPlayInChat.class);
    a(2, PacketPlayInUseEntity.class);
    a(3, PacketPlayInFlying.class);
    a(4, PacketPlayInPosition.class);
    a(5, PacketPlayInLook.class);
    a(6, PacketPlayInPositionLook.class);
    a(7, PacketPlayInBlockDig.class);
    a(8, PacketPlayInBlockPlace.class);
    a(9, PacketPlayInHeldItemSlot.class);
    a(10, PacketPlayInArmAnimation.class);
    a(11, PacketPlayInEntityAction.class);
    a(12, PacketPlayInSteerVehicle.class);
    a(13, PacketPlayInCloseWindow.class);
    a(14, PacketPlayInWindowClick.class);
    a(15, PacketPlayInTransaction.class);
    a(16, PacketPlayInSetCreativeSlot.class);
    a(17, PacketPlayInEnchantItem.class);
    a(18, PacketPlayInUpdateSign.class);
    a(19, PacketPlayInAbilities.class);
    a(20, PacketPlayInTabComplete.class);
    a(21, PacketPlayInSettings.class);
    a(22, PacketPlayInClientCommand.class);
    a(23, PacketPlayInCustomPayload.class);
  }
}
