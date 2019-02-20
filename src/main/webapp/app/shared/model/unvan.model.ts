export interface IUnvan {
    id?: number;
    ad?: string;
    aciklama?: string;
}

export class Unvan implements IUnvan {
    constructor(public id?: number, public ad?: string, public aciklama?: string) {}
}
