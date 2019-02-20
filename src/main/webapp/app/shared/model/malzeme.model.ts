import { IStokTakip } from 'app/shared/model/stok-takip.model';
import { IBirim } from 'app/shared/model/birim.model';
import { IDepo } from 'app/shared/model/depo.model';
import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';

export const enum ParaBirimi {
    TL = 'TL',
    USD = 'USD',
    EUR = 'EUR'
}

export interface IMalzeme {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    tenzilatsizFiyat?: number;
    tenzilatliFiyat?: number;
    taseronFiyat?: number;
    paraBirimi?: ParaBirimi;
    stokTakips?: IStokTakip[];
    birim?: IBirim;
    depo?: IDepo;
    grups?: IMalzemeGrubu[];
}

export class Malzeme implements IMalzeme {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public tenzilatsizFiyat?: number,
        public tenzilatliFiyat?: number,
        public taseronFiyat?: number,
        public paraBirimi?: ParaBirimi,
        public stokTakips?: IStokTakip[],
        public birim?: IBirim,
        public depo?: IDepo,
        public grups?: IMalzemeGrubu[]
    ) {}
}
