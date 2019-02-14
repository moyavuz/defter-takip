import { IIscilik } from 'app/shared/model/iscilik.model';
import { IBirim } from 'app/shared/model/birim.model';
import { IPoz } from 'app/shared/model/poz.model';
import { IMalzemeGrubu } from 'app/shared/model/malzeme-grubu.model';

export interface IMalzeme {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
    fiyatMaliyet?: number;
    isciliks?: IIscilik[];
    birims?: IBirim[];
    poz?: IPoz;
    grups?: IMalzemeGrubu[];
}

export class Malzeme implements IMalzeme {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public kisaltma?: string,
        public fiyatMaliyet?: number,
        public isciliks?: IIscilik[],
        public birims?: IBirim[],
        public poz?: IPoz,
        public grups?: IMalzemeGrubu[]
    ) {}
}
